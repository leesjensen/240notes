package design;

public interface InterfaceSegregationExample {
    byte readByte();

    String readString();

    int readInt();

    // Outside cohesive whole.
    void writeByte(byte b);

    void writeString(String s);

    void writeInt(int i);
}
