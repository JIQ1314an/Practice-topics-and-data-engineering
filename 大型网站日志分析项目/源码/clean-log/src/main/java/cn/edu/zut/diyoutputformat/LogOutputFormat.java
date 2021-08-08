package cn.edu.zut.diyoutputformat;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskID;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @Author 86131
 * @Date 2020/12/21
 * @TIME 9:39
 */
public class LogOutputFormat extends TextOutputFormat {

    public Path getDefaultWorkFile(TaskAttemptContext context, String extension) throws IOException {
        FileOutputCommitter committer = (FileOutputCommitter) getOutputCommitter(context);
        TaskID taskId = context.getTaskAttemptID().getTaskID();
        int partition = taskId.getId();
        partition += 1;
        return new Path(committer.getWorkPath(), "0_access_2020_12_0" + partition + ".log");
    }

}
