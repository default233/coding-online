package com.chen.biz.compiler;

/**
 * @author danger
 * @date 2021/4/24
 */
public class CmdStrings {

    public static final String FILE_NAME_GCC = "main.c";
    public static final String FILE_NAME_GPP = "main.cpp";
    public static final String FILE_NAME_JAVA = "Main.java";
    public static final String FILE_NAME_PYTHON = "main.py";

    /**
     * {
     *         "gcc"    : "gcc main.c -o main -Wall -lm -O2 -std=c99 --static -DONLINE_JUDGE",
     *         "g++"    : "g++ main.cpp -O2 -Wall -lm --static -DONLINE_JUDGE -o main",
     *         "java"   : "javac Main.java",
     *         "ruby"   : "ruby -c main.rb",
     *         "perl"   : "perl -c main.pl",
     *         "pascal" : 'fpc main.pas -O2 -Co -Ct -Ci',
     *         "go"     : '/opt/golang/bin/go build -ldflags "-s -w"  main.go',
     *         "lua"    : 'luac -o main main.lua',
     *         "python2": 'python2 -m py_compile main.py',
     *         "python3": 'python3 -m py_compile main.py',
     *         "haskell": "ghc -o main main.hs",
     * }
     */

    public static final String GCC = "gcc main.c -o main -Wall -lm -O2 -std=c99 --static -DONLINE_JUDGE";
    public static final String GPP = "g++ main.cpp -O2 -Wall -lm --static -DONLINE_JUDGE -o main";
    public static final String JAVA = "javac Main.java";
    public static final String PYTHON2 = "python2 -m py_compile main.py";
    public static final String PYTHON3 = "python -m py_compile main.py";
    public static final String REMOVE_FILE_WINDOWS = "cmd /c rd /s /q ";
    public static final String REMOVE_FILE_LINUX = "rm -rf ";
}
