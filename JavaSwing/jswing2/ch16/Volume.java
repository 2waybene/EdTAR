// Volume.java
// A simple data structure for track volumes on a mixer.
//
public class Volume {
  private int volume;

  public Volume(int v) { setVolume(v); }
  public Volume() { this(50); }

  public void setVolume(int v) { volume = (v < 0 ? 0 : v > 100 ? 100 : v); }
  public void setVolume(Object v) { 
    if (v instanceof String) {
      setVolume(Integer.parseInt((String)v));
    }
    else if (v instanceof Number) {
      setVolume(((Number)v).intValue());
    }
    else if (v instanceof Volume) {
      setVolume(((Volume)v).getVolume());
    }
  }

  public int getVolume() { return volume; }

  public String toString() { return String.valueOf(volume); }
}
