package cn.edu.zut.service.impl;

import cn.edu.zut.service.ILogParse;
import lombok.Data;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 86131
 * @Date 2020/12/20
 * @TIME 15:38
 */
@Data
public class LogParseImpl implements ILogParse, Writable {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String ip = "";
    private String accessTime = "";
    private String requestWay = "";
    private String url = "";
    private String referer = "";
    private String status = "";
    private String pageSize = "";
    private int accessMark = -1;//-1表示其他,0表示注册,1表示登录

    public LogParseImpl parse(String line) {
        this.ip = parseIp(line);
        this.accessTime = parseDate(line);
        this.requestWay = parseRW(line);
        this.url = parseURL(line);
        this.referer = pageReferer(line);
        this.status = parseStatus(line);
        this.pageSize = parsePS(line);
        this.accessMark = parseMark(line);
        return this;
    }

    @Override
    public String parseIp(String line) {
        Pattern ip = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))");
        Matcher m1 = ip.matcher(line);
        if (m1.find()) {
            return m1.group(0).trim();
        }
        return null;
    }

    @Override
    public String parseDate(String line) {
        int beginIndex = line.indexOf("[");
        int endIndex = line.indexOf("+0800");
        String date = line.substring(beginIndex + 1, endIndex).trim();
        Date parseDate = null;
        try {
            parseDate = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = OUTPUT_DATE_FORMAT.format(parseDate);
        return date;
    }

    public String parseRequestBody(String line) {
        int beginIndex = line.indexOf("\"");
        int endIndex = line.lastIndexOf("\"");
        if (endIndex > line.length()) return "";
        return line.substring(beginIndex + 1, endIndex).trim();
    }

    @Override
    public String parseRW(String line) {
        String[] fields = null;
        //rb = "GET /thread-8182-5-1.html HTTP/1.1"
        String rb = parseRequestBody(line);
        if (rb != null) {
            fields = rb.split(" ");
        }
        return fields[0].trim();
    }

    @Override
    public String parseURL(String line) {
        String[] fields = null;
        //rb = "GET /thread-8182-5-1.html HTTP/1.1"
        String rb = parseRequestBody(line);
        if (rb != null) fields = rb.split(" ");
        String url = fields[1].trim();
        if (url.startsWith("/")) url = url.substring(1);
        if (url.startsWith("/")) url = url.substring(1); //    针对数据开头前面有两个"//"的
        if (url.contains("?")) url = url.split("\\?")[0];
        //专门针对登录页面的清洗
        if (url.contains("loginsubmit=yes")) {
            String[] str = url.split("&");
            url = str[0] + "&" + str[1];
        }
        //针对question开头的url
        if (url.startsWith("question")) url = url.split("/")[0];
        if (url.contains("#")) url = url.split("#")[0];//以#做拆分，保留前一部分
        if (url.contains("%")) url = "";//除去url中带%的数据
        return url;
    }

    @Override
    public String pageReferer(String line) {
        String[] fields = null;
        //rb = "GET /thread-8182-5-1.html HTTP/1.1"
        String rb = parseRequestBody(line);
        if (rb != null) {
            fields = rb.split(" ");
        }
        if (fields.length == 3) {
            return fields[2].trim();
        }
        return null;
    }

    @Override
    public String parseStatus(String line) {
        String[] strs = line.trim().split(" ");
        return strs[strs.length - 2];
    }

    @Override
    public String parsePS(String line) {
        String[] strs = line.trim().split(" ");
        //排除没有请求到的页面
        return strs[strs.length - 1].equals("-") ? "0" : strs[strs.length - 1];
    }

    @Override
    public int parseMark(String line) {
        //1.注册的url:forum.php?mod=ajax&inajax=yes&infloat=register&handlekey=register&ajaxmenu=1&action=checkusername&username=hezywn
        //2.登录的url:POST /member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1
        String requestBody = parseRequestBody(line);
        if (requestBody.contains("=register&") && requestBody.contains("&action=check")) return 0;//注册返回
        if (requestBody.startsWith("POST") && requestBody.contains("action=login&loginsubmit=yes")) return 1;//登录返回
        return -1;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(ip);
        dataOutput.writeUTF(accessTime);
        dataOutput.writeUTF(requestWay);
        dataOutput.writeUTF(url);
        dataOutput.writeUTF(referer);
        dataOutput.writeUTF(status);
        dataOutput.writeUTF(pageSize);
        dataOutput.writeInt(accessMark);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.ip = dataInput.readUTF();
        this.accessTime = dataInput.readUTF();
        this.requestWay = dataInput.readUTF();
        this.url = dataInput.readUTF();
        this.referer = dataInput.readUTF();
        this.status = dataInput.readUTF();
        this.pageSize = dataInput.readUTF();
        this.accessMark = dataInput.readInt();
    }

    @Override
    public String toString() {
        return ip + "\t" + url + "\t" + accessMark + "\t" + pageSize;
// 全部数据字段       return ip + "\t" + accessTime + "\t" + requestWay + "\t" + url
//                + "\t" + referer + "\t" + status + "\t" + pageSize + "\t" + accessMark;
    }

}
