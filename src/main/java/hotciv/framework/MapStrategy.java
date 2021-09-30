package hotciv.framework;
import java.util.*;
//Gerald
//Strategy pattern to handle multiple different map layouts for different HotCiv releases
public interface MapStrategy {

    public Map<Position, Tile> setMap();

}
