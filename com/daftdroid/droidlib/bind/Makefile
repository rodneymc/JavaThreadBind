CC=gcc
CFLAGS=-nostartfiles -fpic -shared -ldl -D_GNU_SOURCE -Wall -Werror
OUT=bld
OUTFILES = bld/bind.so bld/bindsetter.so
JNI_AUTO_HEADER=$(OUT)/com_daftdroid_droidlib_bind_ClientBind.h

#Two locations needed to get all the JNI header files
JNI_INCLUDE=/usr/lib/jvm/java-8-oracle/include \
	/usr/lib/jvm/java-8-oracle/include/linux

#Add the includes to the compiler flags

CFLAGS+=$(foreach d, $(JNI_INCLUDE), -I$d)

all: $(OUTFILES)

$(OUTFILES): $(OUT)/%.so: %.c $(JNI_AUTO_HEADER)
	$(CC) $(CFLAGS) -c $< -o $@

$(JNI_AUTO_HEADER): ClientBind.java
	javac ClientBind.java -d $(OUT) -h $(OUT)

.PHONY: clean
clean:
	rm bld/* -rf

