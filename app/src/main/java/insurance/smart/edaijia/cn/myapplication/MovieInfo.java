package insurance.smart.edaijia.cn.myapplication;

import java.util.List;

/**
 *
 */

public class MovieInfo {
    public int page;
    public int total_results;
    public int total_pages;

    public List<MovieBean> results;

    public static class MovieBean {
        public String poster_path;
        public boolean adult;
        public String overview;
        public String release_date;
        public int id;
        public String original_title;
        public String original_language;
        public String title;
        public String backdrop_path;
        public double popularity;
        public int vote_count;
        public boolean video;
        public double vote_average;
        public List<Integer> genre_ids;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }
}
