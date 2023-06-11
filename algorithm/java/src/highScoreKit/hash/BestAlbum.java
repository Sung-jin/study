package highScoreKit.hash;

import java.util.*;
import java.util.stream.IntStream;

/*
스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.

속한 노래가 많이 재생된 장르를 먼저 수록합니다.
장르 내에서 많이 재생된 노래를 먼저 수록합니다.
장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.

제한사항
genres[i]는 고유번호가 i인 노래의 장르입니다.
plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
장르 종류는 100개 미만입니다.
장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
모든 장르는 재생된 횟수가 다릅니다.
 */
public class BestAlbum {
    public int[] solution(String[] genres, int[] plays) {
        HashMap<String, GenreInfo> playMap = new HashMap<>();

        for (int i = 0; i < genres.length; i++) {
            String genre = genres[i];
            playMap.put(
                    genre,
                    playMap.getOrDefault(genre, new GenreInfo(genre))
                            .addPlayCount(plays[i], i)
            );
        }

        return playMap.values()
                .stream()
                .sorted(Comparator.comparing(GenreInfo::getTotalPlay).reversed())
                .flatMapToInt(GenreInfo::getPlayIndex)
                .toArray();
    }

    private class GenreInfo {
        private String genre;
        private Integer totalPlay;
        private Integer maxPlayCount;
        private Integer nextMaxPlayCount;
        private Integer maxPlayIndex;
        private Integer nextMaxPlayIndex;

        public GenreInfo(String genre) {
            this.genre = genre;
            this.totalPlay = 0;
            this.maxPlayCount = 0;
            this.nextMaxPlayCount = 0;
        }

        public GenreInfo addPlayCount(Integer playCount, Integer index) {
            if (playCount > this.maxPlayCount) {
                this.nextMaxPlayCount = this.maxPlayCount;
                this.nextMaxPlayIndex = this.maxPlayIndex;
                this.maxPlayCount = playCount;
                this.maxPlayIndex = index;
            } else if (playCount > this.nextMaxPlayCount) {
                this.nextMaxPlayCount = playCount;
                this.nextMaxPlayIndex = index;
            }
            this.totalPlay += playCount;

            return this;
        }

        public Integer getTotalPlay() {
            return this.totalPlay;
        }

        public IntStream getPlayIndex() {
            if (this.nextMaxPlayCount != 0) {
                return IntStream.of(this.maxPlayIndex, this.nextMaxPlayIndex);
            }
            return IntStream.of(this.maxPlayIndex);
        }
    }
}

/*
// 자료구조/알고리즘: 해시맵 & 정렬 & 힙 (PriorityQueue)
// 시간 복잡도: O(nlogn)
// 공간 복잡도: O(n)

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        Map<String, Genre> genreMap = new HashMap<>();
        for (int i = 0; i < genres.length; i++) { // O(nlogn) 시간
            String genreName = genres[i];
            int playCount = plays[i];

            Genre genre = genreMap.getOrDefault(genreName, new Genre(genreName));
            genre.playCount += playCount;
            genre.songs.add(new Song(i, playCount)); // // PriorityQueue에서 add()은 O(logn) 시간
            genreMap.put(genreName, genre);
        }

        List<Integer> bestAlbum = new ArrayList<>();
        Genre[] sortedGenres = genreMap.values().toArray(new Genre[genreMap.size()]);
        Arrays.sort(sortedGenres, (a, b) -> b.playCount - a.playCount); // O(nlogn) 시간

        for (Genre genre : sortedGenres) { // O(nlogn) 시간
            PriorityQueue<Song> songs = genre.songs;
            int songsToAdd = Math.min(songs.size(), 2);
            while (!songs.isEmpty() && songsToAdd-- > 0) { // 최대 2번 돌기 때문에 O(logn) 시간으로 봅니다
                bestAlbum.add(songs.poll().id); // PriorityQueue에서 poll()은 O(logn) 시간
            }
        }

        return bestAlbum.stream().mapToInt(Integer::intValue).toArray();
    }

    class Genre {
        String name;
        int playCount;
        PriorityQueue<Song> songs; // Max Heap으로 사용

        public Genre(String name) {
            this.name = name;
            this.playCount = 0;
            this.songs = new PriorityQueue<>((a, b) -> b.playCount - a.playCount);
        }
    }

    class Song {
        int id;
        int playCount;

        public Song(int id, int playCount) {
            this.id = id;
            this.playCount = playCount;
        }
    }
}
 */
