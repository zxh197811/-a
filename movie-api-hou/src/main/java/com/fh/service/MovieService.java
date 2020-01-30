package com.fh.service;

import com.fh.model.Movie;
import com.fh.model.MovieQuery;
import com.fh.model.Photo;
import com.fh.util.DataTableResult;
import com.fh.util.ServerResponse;

import java.util.List;

public interface MovieService {
    DataTableResult pageselect(MovieQuery movieQuery);

    void addMovie(Movie movie);

    Movie selectMovieById(Integer id);

    void updateMovie(Movie movie);

    Movie selectMovieById1(Integer id);

    void deleteMovie(Integer id);

    void deleteAllMovie(List<Integer> ids);

    ServerResponse imagemanage(Integer movieid);

    void addImage(Photo photo);

    void deleteimage(Integer imageid);

    void deleteAllimage(List<Integer> ids);

    Photo selectImageById(Integer imageid);
}
