CC=gcc
CFLAGS=-fpic -D_GNU_SOURCE -Wall -Werror -Wextra -Wmissing-prototypes
LDFLAGS=-nostartfiles -shared -ldl
OUT=bld
OUTFILES = bld/bind.so bld/bindsetter.so
JNI_AUTO_HEADER=$(OUT)/com_daftdroid_droidlib_bind_ClientBind.h
CFLAGS+=-include $(JNI_AUTO_HEADER)
CFLAGS+=$(EXTRA_CFLAGS)

ifndef JNI_INCLUDE

#If the user hasn't supplied JNI_INCLUDE path
#then use a default based on JAVA_HOME

ifndef JAVA_HOME
$(error JAVA_HOME not set)
endif

JNI_INCLUDE=$(JAVA_HOME)/include \
	$(JAVA_HOME)/include/linux
endif

#Add the includes to the compiler flags

CFLAGS+=$(foreach d, $(JNI_INCLUDE), -I$d)

all: $(OUTFILES)

$(OUT):
	mkdir $(OUT)

$(OUTFILES): $(OUT)/%.so: %.c $(JNI_AUTO_HEADER) Makefile $(OUT)
	$(CC) $(CFLAGS) $< -o $@ $(LDFLAGS)

$(JNI_AUTO_HEADER): ClientBind.java Makefile $(OUT)
	javac ClientBind.java -d $(OUT) -h $(OUT)

.PHONY: clean
clean:
	rm bld/* -rf
	echo $(EXTRA_CFLAGS)
