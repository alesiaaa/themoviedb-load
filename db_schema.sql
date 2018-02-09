--
-- PostgreSQL schema for 'Movie Data' task
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.movie_director DROP CONSTRAINT director_id_fkey;
ALTER TABLE ONLY public.movie_director DROP CONSTRAINT movie_id_fkey;
ALTER TABLE ONLY public.director DROP CONSTRAINT director_pkey;
ALTER TABLE ONLY public.now_playing_movies DROP CONSTRAINT movie_pkey;
DROP TABLE public.movie_director;
DROP TABLE public.director;
DROP SEQUENCE public.director_director_id_seq;
DROP TABLE public.now_playing_movies;
DROP SEQUENCE public.now_playing_movies_movie_id_seq;
--DROP EXTENSION plpgsql;
--DROP SCHEMA public;

SET search_path = public, pg_catalog;

-- CREATE SCHEMA public;
--ALTER SCHEMA public OWNER TO postgres;

CREATE SEQUENCE now_playing_movies_movie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.now_playing_movies_movie_id_seq OWNER TO postgres;

CREATE TABLE now_playing_movies (
    movie_id integer DEFAULT nextval('now_playing_movies_movie_id_seq'::regclass) NOT NULL,
    title character varying(80) NOT NULL,
    original_title character varying(80) NOT NULL,
    description text NOT NULL
);

ALTER TABLE public.now_playing_movies OWNER TO postgres;

CREATE SEQUENCE director_director_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.director_director_id_seq OWNER TO postgres;

CREATE TABLE director (
    director_id integer DEFAULT nextval('director_director_id_seq'::regclass) NOT NULL,
    name character varying(80) NOT NULL,
    imdb_link character varying(80) NOT NULL
);

ALTER TABLE public.director OWNER TO postgres;

CREATE TABLE movie_director (
    movie_id integer NOT NULL,
    director_id integer NOT NULL
);

ALTER TABLE public.movie_director OWNER TO postgres;

ALTER TABLE ONLY now_playing_movies
    ADD CONSTRAINT movie_pkey PRIMARY KEY (movie_id);

ALTER TABLE ONLY director
    ADD CONSTRAINT director_pkey PRIMARY KEY (director_id);

ALTER TABLE ONLY movie_director
    ADD CONSTRAINT movie_id_fkey FOREIGN KEY (movie_id) REFERENCES now_playing_movies(movie_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY movie_director
    ADD CONSTRAINT director_id_fkey FOREIGN KEY (director_id) REFERENCES director(director_id) ON UPDATE CASCADE ON DELETE RESTRICT;

