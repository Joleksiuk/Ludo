package pl.rokolujka.springreactludo.game.board;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BoardLayoutLoader {

    private static final String BOARDS_LOCATION_ROOT = "classpath:boards/";

    private final ResourceLoader resourceLoader;

    public Resource loadBoardLayout(BoardEnum boardEnum) {
        return resourceLoader.getResource(BOARDS_LOCATION_ROOT + boardEnum.getFileName());
    }
}
