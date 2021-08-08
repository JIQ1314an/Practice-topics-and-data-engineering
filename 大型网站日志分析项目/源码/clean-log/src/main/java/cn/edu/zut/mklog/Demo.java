package cn.edu.zut.mklog;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    private static String extension = "12_0";//默认情况

    public static void main(String[] args) throws Exception {
        //传入的参数为 日-月--时 格式的字符串， 使用默认生成前7天日志 ，避免移动到flume监控文件出现名字相同的现象
        if (args.length == 1) {
            extension = args[0] + "_";
        }
        //默认前七天日志
        int start = 0;
        int end = 7;
        //自定义生成天数的日志
        if (args.length == 2) {
            start = Integer.parseInt(args[0]);
            end = Integer.parseInt(args[1]);
        }
        //自定义生成天数的日志和文件日期格式，避免移动到flume监控文件出现名字相同的现象
        if (args.length == 3) {
            start = Integer.parseInt(args[0]);
            end = Integer.parseInt(args[1]);
            extension = args[2] + "_";
        }
//		System.out.println(getRandomIp());
        Object[] String_ss = getSet().toArray();
        System.out.println("******正在生产数据******");
//		System.out.println(String_ss[1]);
        for (int j = start; j < end; j++) {//生成7的日志 0到7
            //for(int i = 10;i<15;i++) {//生成很多人的日志，自己的可以去掉这个循环
            File file = new File("/opt/data/" + 00 + "_access_2020_" + extension + (j + 1) + ".log");
            if (!file.exists()) {
                file.createNewFile();
            }
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                Random rand = new Random();

                while (file.length() / (1024 * 1024) < 50) {
                    Date date = randomDate("2020-12-0" + (j + 1), "2020-12-0" + (j + 2));
                    String Time_str = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH).format(date);

                    String str = getRandomIp() + " - - [" + Time_str + " +0800] " + String_ss[rand.nextInt(String_ss.length)] + " 200 " + (rand.nextInt(1100) + 100);
                    bw.write(str);
                    bw.newLine();//换行
                }

                bw.close();
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("******生产成功******");
        //}
    }

    public static Set<String> getSet() {

        try {
            // read file content from file
            FileReader reader = new FileReader("/opt/readFile/access_2017_05_30.log");
            BufferedReader br = new BufferedReader(reader);
            Set<String> String_ss = new HashSet<String>();
            String str = null;

            while ((str = br.readLine()) != null) {
                //1.将正则表达式封装成对象Patten 类来实现
                Pattern pattern = Pattern.compile("\"(.*)\"");
                //2.将字符串和正则表达式相关联
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    String_ss.add(matcher.group());
                }
            }

            br.close();
            reader.close();

            return String_ss;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;


    }


    public static String getRandomIp() {
        //ip范围
        int[][] range = {{607669792, 607674079},//36.56.0.0-36.63.255.255
                {1784627776, 1784636351},//106.80.0.0-106.95.255.255
                {-1236571104, -1236419137},//182.80.0.0-182.92.255.255
                {-768713536, -768706209}//210.25.0.0-210.47.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(4);
        String ip = num2ip(range[index][0]
                + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";
        //0x代表16进制，FF=11111111(二进制),
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "."
                + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }


    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if (start.getTime() >= end.getTime()) {
                return null;
            }

            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }


}
