package cn.edu.zut.clean;

import cn.edu.zut.diyoutputformat.LogOutputFormat;
import cn.edu.zut.service.impl.LogParseImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;


/**
 * @Author 86131
 * @Date 2020/12/20
 * @TIME 15:22
 */
public class LogCleanDirver {
    private static String srcPrefix = "/flume/upload/";
    private static String dstPrefix = "/CleanedData/";
    //默认读取文件
    private static String src = srcPrefix + "11-00";
    private static String dst = dstPrefix + "11-00";
    private static String htabPath = "/hive/warehouse/lams.db/website_log";

    public static void main(String[] args) {
        //传入的参数为日期格式： 月-日(必须在/flume/upload/文件下存在此文件夹)
        if (args.length == 1) {
            singleParameter(args);
        }
        //第一个参数为日期，用于读取原数据文件，便于做清洗；第二个为hivetab所在的路径
        if (args.length == 2) {
            singleParameter(args);
            htabPath = args[1];
        }
        LogCleanConfig lcc = new LogCleanConfig(src, dst);
        try {
            //lcc.run();//本地测试使用
            lcc.reduceNum();//删除已存在的文件，并返回redue任务数
            if (lcc.run())//reduce任务数一个，压力比较大
                lcc.move(htabPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void singleParameter(String[] args) {
        String[] srcPath = src.trim().split("/");
        String[] dstPath = src.trim().split("/");
        src = src.substring(0, src.length() - srcPath[srcPath.length - 1].length());
        dst = dst.substring(0, dst.length() - dstPath[dstPath.length - 1].length());
        src += args[0];
        dst += args[0];
    }


}

class LogCleanConfig {
    Configuration conf = new Configuration();
    private String src;
    private String dst;
    private FileSystem fs;

    public LogCleanConfig(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    public FileSystem getFileSystem() throws Exception {
        return FileSystem.get(new URI("hdfs://node120:9000"), conf, "root");
    }

    public int reduceNum() throws Exception {
        fs = getFileSystem();
        if (!fs.exists(new Path(src))) throw new Exception("***源文件目录不存在***");
        if (fs.exists(new Path(dst))) fs.delete(new Path(dst), true);
        return fs.listStatus(new Path(src)).length;
    }

    public boolean run() throws Exception {
        System.out.println("***********开始清洗数据**********");
        Job job = Job.getInstance(conf, "LC");
        job.setMapperClass(LogCleanMapper.class);
        //job.setReducerClass(LogCleanReducer.class);
        //集群配置
        job.setJarByClass(LogCleanDirver.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(LogParseImpl.class);
        //job.setNumReduceTasks(fileNum);
        FileInputFormat.setInputPaths(job, new Path(src));//src
        FileOutputFormat.setOutputPath(job, new Path(dst));//dst
        job.setOutputFormatClass(LogOutputFormat.class);
        System.out.println("***********正在清洗...**********");
        boolean flag = job.waitForCompletion(true);
        System.out.println(flag ? "***********清洗完成**********" : "***********清洗失败**********");
        return flag;
    }

    public void move(String htabPath) throws Exception {
        if (fs == null) fs = getFileSystem();
        if (!fs.exists(new Path(htabPath))) return;
        String fileName;
        if (!htabPath.endsWith("/")) htabPath += "/";
        System.out.println("******正在移动文件******");
        FileStatus[] statuses = fs.listStatus(new Path(dst));
        Path resultDirPath;
        Path resultFilePath;
        int count = 0;
        for (FileStatus status : statuses) {
            fileName = status.getPath().getName();
            if (!fileName.endsWith(".log")) continue;
            String[] strs = getFileName(count++).split("\t");
            resultDirPath = new Path(htabPath + "createtime=" + strs[0]);
            if (!fs.exists(resultDirPath)) fs.mkdirs(resultDirPath);//创建分区文件夹
            if (!dst.endsWith("/")) dst += "/";
            resultFilePath = new Path(htabPath + "createtime=" + strs[0] + "/" + strs[1]);
            if (!fs.exists(resultFilePath)) {
                fs.rename(new Path(dst + fileName), resultFilePath);
                fs.delete(new Path(dst + fileName), true);
                System.out.println("*****移动成功*****");
            } else {
                System.out.println("*****移动失败,文件重名或其他*****");
            }
        }

    }

    public String getFileName(int count) throws Exception {
        String[] srcPath = src.trim().split("/");
        String prefix = srcPath[srcPath.length - 1].trim();
        String result = prefix + "_";
        fs = getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(new Path(src));
        int countNum = 0;
        for (FileStatus fileStatus : fileStatuses) {
            if (countNum != count) {
                countNum++;
                continue;
            }
            //就是flume上传到hdfs上的文件名，就是多加个时间的前缀
            result += fileStatus.getPath().getName();
            break;
        }
        return prefix + "\t" + result;

    }

}

class LogCleanMapper extends Mapper<LongWritable, Text, NullWritable, LogParseImpl> {
    private LogParseImpl logParse = new LogParseImpl();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String requestBody = logParse.parseRequestBody(line);
        //requestBody= "GET /thread-8182-5-1.html HTTP/1.1"这种形式的才会保留
        if (requestBody.split(" ").length == 3) {
            String url = logParse.parseURL(line);
            //过滤掉图片,css,js等静态资源以及其他脏数据
            if (requestBody.startsWith("GET /static")
                    || requestBody.startsWith("GET /source")
                    || requestBody.startsWith("GET //static")
                    || url.endsWith(".jpg")
                    || url.endsWith(".png")
                    || url.endsWith(".gif")
                    || url.endsWith(".css")
                    || url.endsWith(".js")
                    || url.endsWith(".rar")
                    || url.endsWith(".zip")
                    || url.endsWith(".txt")
                    || url.endsWith(".swf")
                    || url.endsWith(".xml")
                    || url.equals("")
            ) return;
            logParse = logParse.parse(line);
            //保持原来的顺序
            context.write(NullWritable.get(), logParse);
        }
    }
}
