package cn.edu.zut.service;

/**
 * @Author 86131
 * @Date 2020/12/20
 * @TIME 15:23
 */
public interface ILogParse {

    String parseIp(String line);

    String parseDate(String line);

    String parseRW(String line);

    String parseURL(String line);

    String pageReferer(String line);

    String parseStatus(String line);

    String parsePS(String line);

    int parseMark(String line);
}
