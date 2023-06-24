package highScoreKit.dfsAndBfs;

import java.util.*;
import java.util.stream.Collectors;

/*
지형은 각 변이 x축, y축과 평행한 직사각형이 겹쳐진 형태로 표현하며, 캐릭터는 이 다각형의 둘레(굵은 선)를 따라서 이동합니다.

만약 직사각형을 겹친 후 다음과 같이 중앙에 빈 공간이 생기는 경우, 다각형의 가장 바깥쪽 테두리가 캐릭터의 이동 경로가 됩니다.

단, 서로 다른 두 직사각형의 x축 좌표 또는 y축 좌표가 같은 경우는 없습니다.
서로 다른 두 직사각형이 꼭짓점에서 만나거나, 변이 겹치는 경우 등은 없습니다.
지형이 2개 이상으로 분리된 경우도 없습니다.
한 직사각형이 다른 직사각형 안에 완전히 포함되는 경우 또한 없습니다.

지형을 나타내는 직사각형이 담긴 2차원 배열 rectangle, 초기 캐릭터의 위치 characterX, characterY, 아이템의 위치 itemX, itemY가 solution 함수의 매개변수로 주어질 때, 캐릭터가 아이템을 줍기 위해 이동해야 하는 가장 짧은 거리를 return 하도록 solution 함수를 완성해주세요.

제한사항
rectangle의 세로(행) 길이는 1 이상 4 이하입니다.
rectangle의 원소는 각 직사각형의 [좌측 하단 x, 좌측 하단 y, 우측 상단 x, 우측 상단 y] 좌표 형태입니다.
    직사각형을 나타내는 모든 좌표값은 1 이상 50 이하인 자연수입니다.
    서로 다른 두 직사각형의 x축 좌표, 혹은 y축 좌표가 같은 경우는 없습니다.
    문제에 주어진 조건에 맞는 직사각형만 입력으로 주어집니다.
charcterX, charcterY는 1 이상 50 이하인 자연수입니다.
    지형을 나타내는 다각형 테두리 위의 한 점이 주어집니다.
itemX, itemY는 1 이상 50 이하인 자연수입니다.
    지형을 나타내는 다각형 테두리 위의 한 점이 주어집니다.
캐릭터와 아이템의 처음 위치가 같은 경우는 없습니다.

전체 배점의 50%는 직사각형이 1개인 경우입니다.
전체 배점의 25%는 직사각형이 2개인 경우입니다.
전체 배점의 25%는 직사각형이 3개 또는 4개인 경우입니다.
 */
public class PickUpItem {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        Rectangle[] rectangles = new Rectangle[rectangle.length];
        Queue<int[]> queue = new LinkedList<>();
        int moveCount = 0;

        for (int i = 0; i < rectangle.length; i++) {
            rectangles[i] = new Rectangle(rectangle[i], characterX, characterY);
        }

        queue.offer(new int[]{characterX, characterY});

        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            moveCount++;

            for (int i = 0; i < queueSize; i++) {
                int[] nextPoint = queue.poll();
                int x = nextPoint[0], y = nextPoint[1];

                for (int j = 0; j < rectangles.length; j++) {
                    Rectangle info = rectangles[j];

                    if (!info.isUnderContain(x, y)) {
                        int[][] possibleNexts = info.getPossibleNextMove(x, y);

                        for (int[] possibleNext : possibleNexts) {
                            if (possibleNext[0] == itemX && possibleNext[1] == itemY) return moveCount;
                            else queue.offer(possibleNext);
                        }
                    }
                }
            }
        }

        return moveCount;
    }

    class Rectangle {
        int[][] points;
        boolean[] visited;
        int x1;
        int x2;
        int y1;
        int y2;

        public Rectangle(int[] value, int startX, int startY) {
            x1 = value[0];
            x2 = value[2];
            y1 = value[1];
            y2 = value[3];
            int w = x2 - x1;
            int h = y2 - y1;
            int round = w * 2 + h * 2;
            points = new int[round][];
            visited = new boolean[round];

            for (int i = 0; i <= w; i++) {
                int x = x1 + i;
                points[i * 2] = new int[]{x, y1};
                points[i * 2 + 1] = new int[]{x, y2};

                if (x == startX && y1 == startY) visited[i * 2] = true;
                if (x == startX && y2 == startY) visited[i * 2 + 1] = true;
            }
            for (int i = 1; i < h; i++) {
                int y = y1 + i;
                points[w * 2 + i * 2] = new int[]{x1, y};
                points[w * 2 + i * 2 + 1] = new int[]{x2, y};

                if (x1 == startX && y == startY) visited[w * 2 + i * 2] = true;
                if (x2 == startX && y == startY) visited[w * 2 + i * 2 + 1] = true;
            }
        }

        public boolean isUnderContain(int x, int y) {
            boolean containX = x1 < x && x < x2;
            boolean containY = y1 < y && y < y2;

            return containX && containY;
            // 요 필터만 내부에 안 겹치는 라인만 딸 수 있는 형태로 변경하면 됨.
        }

        public int[][] getPossibleNextMove(int x, int y) {
            List<int[]> nextPoints = new ArrayList<>();

            for (int i = 0; i < points.length; i++) {
                int px = points[i][0], py = points[i][1];
                int subX = Math.abs(px - x);
                int subY = Math.abs(py - y);

                if (!visited[i] && ((subX == 1 && py == y) || (subY == 1 && px == x))) {
                    visited[i] = true;
                    nextPoints.add(points[i]);
                }
            }

            return nextPoints.stream().toArray(int[][]::new);
        }
    }
}

// 전체적으로 이렇게 하지 않고 뭔가 방법이 있을 것 같은데.. 감이 안옴