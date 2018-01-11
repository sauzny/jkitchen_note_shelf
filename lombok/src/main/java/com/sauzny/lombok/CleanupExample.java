package com.sauzny.lombok;

import lombok.Cleanup;
import java.io.*;

public class CleanupExample {
    public static void main(String[] args) throws IOException {
        @Cleanup
        InputStream in = new FileInputStream(args[0]);
        @Cleanup
        OutputStream out = new FileOutputStream(args[1]);
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
            if (r == -1)
                break;
            out.write(b, 0, r);
        }
    }
}


class CleanupExample1 {
    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream(args[0]);
        try {
            OutputStream out = new FileOutputStream(args[1]);
            try {
                byte[] b = new byte[10000];
                while (true) {
                    int r = in.read(b);
                    if (r == -1)
                        break;
                    out.write(b, 0, r);
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
