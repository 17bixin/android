package com.gestures.heart.util;


import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25623 on 2017/12/6.
 */

public class VideoUtils {

    private Calendar mCalendar = Calendar.getInstance();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd-kkmmss-SSS");

    /**
     *
     * @param videosToMerge 需要合并的视频的路径集合
     * @param output   输出的视频
     */
    public static void merge(List<String> videosToMerge, String output){
        int count = videosToMerge.size();
        try {
            Movie[] inMovies = new Movie[count];
            for (int i = 0; i < count; i++) {
                inMovies[i] = MovieCreator.build(videosToMerge.get(i));
            }

            List<Track> videoTracks = new LinkedList<>();
            List<Track> audioTracks = new LinkedList<>();

            //提取所有视频和音频的通道
            for (Movie m : inMovies) {
                for (Track t : m.getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                    if (t.getHandler().equals("")) {

                    }
                }
            }

            //添加通道到新的视频里
            Movie result = new Movie();
            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks
                        .toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks
                        .toArray(new Track[videoTracks.size()])));
            }
            Container mp4file = new DefaultMp4Builder()
                    .build(result);


            //开始生产mp4文件
            FileOutputStream fos =  new FileOutputStream(new File(output));
            FileChannel fco = fos.getChannel();
            mp4file.writeContainer(fco);
            fco.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 为输出文件名生成时间
     *
     * @return
     */
    private String generateTimeString4OutputName() {
        return "SoloVideo-" + mDateFormat.format(mCalendar.getTime());
    }

    /**
     * 生成输出文件名, 如果outputFormat为空，则输出文件使用与输入文件相同的格式
     *
     * @param outputPath
     * @return
     */
    private String generateOutputFile4Video( String outputPath) {
        StringBuilder outputFileStr = new StringBuilder();
            outputFileStr.append(outputPath)
                    .append("/")
                    .append(generateTimeString4OutputName())
                    .append(".mp4");

        File outputFile = new File(outputFileStr.toString());
        for (int i = 0; outputFile.exists(); i++) {
            if (i != 0) {
                outputFileStr.delete(outputFileStr.lastIndexOf("-"), outputFileStr.lastIndexOf("."));
            }
            outputFileStr.insert(outputFileStr.lastIndexOf("."), "-" + i);
            outputFile = new File(outputFileStr.toString());
        }
        return outputFileStr.toString();
    }
}