package com.example.media.util;

import com.example.media.classes.Playlist;
import com.example.media.classes.Track;
import com.example.media.classes.Video;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Клас-утиліта для запису статистики у вихідні файли.
 *
 * Студенти мають реалізувати методи нижче з використанням:
 * - Stream API
 * - лямбда-виразів
 * - method references
 *
 * Формат вихідних файлів має бути простим і "людиночитабельним"
 * (див. приклади у README.md).
 */
public class MediaStatisticsWriter {

    /**
     * Запис статистики по музичних треках у файл.
     *
     * Очікуваний формат файлу `tracks_output.txt`:
     * --------------------------------------------
     * Tracks count: <загальна кількість треків>
     * Average duration: <середня тривалість у секундах> seconds
     *
     * Top 3 tracks by rating:
     * 1. <назва треку> (rating <рейтинг>)
     * 2. <назва треку> (rating <рейтинг>)
     * 3. <назва треку> (rating <рейтинг>)
     *
     * Pop tracks:
     * - <назва треку>
     * - <назва треку>
     * --------------------------------------------
     *
     * Пояснення:
     * - Tracks count → кількість елементів у плейлисті
     * - Average duration → середня тривалість у секундах
     * - Top 3 → відсортовані за рейтингом спаданням,
     *   при однаковому рейтингу брати найдовші
     * - Pop tracks → усі треки, у яких жанр == "Pop"
     */
    public static void writeTrackStats(Playlist<Track> playlist, String filename) throws IOException {
        List<Track> tracks = playlist.getItems();

        int count = tracks.size();
        double averageDuration = tracks.stream()
                .mapToInt(Track::getDuration)
                .average()
                .orElse(0);

        //Рейтинг ТОП-3
        List<Track> top3 = tracks.stream()
                .sorted(Comparator.comparingInt(Track::getRating).reversed()
                        .thenComparing(Track::getDuration, Comparator.reverseOrder()))
                .limit(3)
                .toList();

        //РОР треки
        List<Track> popTracks = tracks.stream()
                .filter(t -> t.getGenre().equalsIgnoreCase("Pop"))
                .toList();

        StringBuilder sb = new StringBuilder();
        sb.append(" Tracks count: ").append(count).append("\n");
        sb.append(" Average duration: ").append((int) averageDuration).append(" seconds\n\n");

        sb.append(" TOP 3 tracks by rating: \n");
        for (int i = 0; i < top3.size(); i++) {
            Track t = top3.get(i);
            sb.append(i + 1).append(". ").append(t.getTitle())
                    .append(" (rating ").append(t.getRating()).append(")\n");
        }

        sb.append(" \nPOP tacks: \n");
        popTracks.forEach(t -> sb.append("- ").append(t.getTitle()).append(" \n"));

        Files.write(Paths.get(filename), sb.toString().getBytes());
    }



        // TODO: Реалізуйте цей метод
        // Підказки:
        // - Використайте playlist.getItems().size() для підрахунку кількості
        // - Використайте stream().mapToInt(Track::getDuration).average() для середньої тривалості
        // - Використайте stream().sorted(...).limit(3) для топ-3 за рейтингом
        // - Використайте stream().filter(t -> t.getGenre().equalsIgnoreCase("Pop")) для відбору Pop-треків
        // - Запишіть результати у файл через PrintWriter або Files.write()


    /**
     * Запис статистики по відео у файл.
     *
     * Очікуваний формат файлу `videos_output.txt`:
     * --------------------------------------------
     * Videos count: <загальна кількість відео>
     * Average duration: <середня тривалість у секундах> seconds
     *
     * Top 3 videos by views:
     * 1. <назва відео> (<кількість переглядів> views)
     * 2. <назва відео> (<кількість переглядів> views)
     * 3. <назва відео> (<кількість переглядів> views)
     *
     * Education videos:
     * - <назва відео>
     * - <назва відео>
     * --------------------------------------------
     *
     * Пояснення:
     * - Videos count → кількість елементів у плейлисті
     * - Average duration → середня тривалість у секундах
     * - Top 3 → відсортовані за views спаданням
     * - Education videos → усі відео, у яких category == "Education"
     */
    public static void writeVideoStats(Playlist<Video> playlist, String filename) throws IOException {
        List<Video> videos = playlist.getItems();

        int count = videos.size();
        double averageDuration = videos.stream()
                .mapToInt(Video::getDuration)
                .average()
                .orElse(0);

        //ТОР3 відіва за переглядами
        List<Video> top3 = videos.stream()
                .sorted(Comparator.comparingInt(Video::getViews).reversed())
                .limit(3)
                .toList();

        // Education video
        List<Video> educationVideos = videos.stream()
                .filter(v -> v.getCategory().equalsIgnoreCase("Education"))
                .toList();

        StringBuilder sb = new StringBuilder();
        sb.append(" Videos count: ").append(count).append("\n");
        sb.append(" Average duration: ").append((int) averageDuration).append(" seconds\n\n");

        sb.append(" Top 3 videos by views: \n");
        for (int i = 0; i < top3.size(); i++) {
            Video v = top3.get(i);
            sb.append(i + 1).append(". ").append(v.getTitle())
                    .append(" (").append(v.getViews()).append(" views)\n");
        }

        sb.append("\nEducation videos:\n");
        educationVideos.forEach(v -> sb.append("- ").append(v.getTitle()).append("\n"));
        Files.write(Paths.get(filename), sb.toString().getBytes());
    }

        // TODO: Реалізуйте цей метод
        // Підказки:
        // - Використайте playlist.getItems().size() для підрахунку кількості
        // - Використайте stream().mapToInt(Video::getDuration).average() для середньої тривалості
        // - Використайте stream().sorted(...).limit(3) для топ-3 за views
        // - Використайте stream().filter(v -> v.getCategory().equalsIgnoreCase("Education")) для Education-відео
        // - Запишіть результати у файл через PrintWriter або Files.write()
    }

