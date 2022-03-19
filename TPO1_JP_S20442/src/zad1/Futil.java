package zad1;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Futil implements FileVisitor<Path> {

    private static Charset SRC_FILE_ENCODING = Charset.forName("Cp1250");
    private static Charset DEST_FILE_ENCODING = StandardCharsets.UTF_8;
    private static Path outputPath;
    private static FileChannel outputFileChannel;
    private FileChannel inputFileChannel;
    private ByteBuffer buffer;
    private static String outFileName;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile() && !file.getFileName().toString().equals(outFileName)) {
            RandomAccessFile file_tmp = new RandomAccessFile(file.toString(), "r");
            inputFileChannel = file_tmp.getChannel();
            MappedByteBuffer buf;
            buf = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int) inputFileChannel.size());
            CharBuffer cbuf = SRC_FILE_ENCODING.decode(buf);
            cbuf.rewind();
            outputFileChannel.write(DEST_FILE_ENCODING.encode(cbuf));
            inputFileChannel.close();
        }
        return FileVisitResult.CONTINUE;
    }

    public static void processDir(String dirName, String resultFileName) {
        outFileName = resultFileName;
        String outputString = dirName + "/" + resultFileName;
        System.out.println(outputString);


        try {
//            outputFileChannel = FileChannel.open(outputPath, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
//            outputFileChannel = new FileOutputStream(resultFileName).getChannel();
            RandomAccessFile outFile = new RandomAccessFile(outputString, "rw");
            outputFileChannel = outFile.getChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
//            pw = new PrintWriter(outFileName, String.valueOf(DEST_FILE_ENCODING));
            Path walkFileTree = Files.walkFileTree(Paths.get(dirName), new Futil());
            outputFileChannel.close();
        } catch (IOException e) {
            System.err.println("processDir -> IOException !");
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.err.println("visitFileFailed -> IOException !");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
