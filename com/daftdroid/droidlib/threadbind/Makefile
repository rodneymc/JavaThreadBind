#(c) rodney@daftdroid.com 2017

#This library is free software; you can redistribute it and/or
#modify it under the terms of the GNU Lesser General Public
#License as published by the Free Software Foundation; either
#version 2.1 of the License, or (at your option) any later version.
#
#This library is distributed in the hope that it will be useful,
#but WITHOUT ANY WARRANTY; without even the implied warranty of
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
#Lesser General Public License for more details.
#
#You should have received a copy of the GNU Lesser General Public
#License along with this library; if not, write to the Free Software
#Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA


CC=gcc
CFLAGS=-fpic -D_GNU_SOURCE -Wall -Werror -Wextra -Wmissing-prototypes
LDFLAGS=-nostartfiles -shared -ldl
OUT=bld
OUTFILES = bld/bind.so bld/threadbind.so
JAVA_PACKAGE=com.daftdroid.droidlib.threadbind
JAVA_CLASS=ThreadBind
JNI_AUTO_HEADER=$(OUT)/$(subst .,_,$(JAVA_PACKAGE))_$(JAVA_CLASS).h
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
	#
	# Don't forget to run 'make test'
	#


$(OUT):
	mkdir $(OUT)

$(OUTFILES): $(OUT)/%.so: %.c $(JNI_AUTO_HEADER) Makefile $(OUT)
	$(CC) $(CFLAGS) $< -o $@ $(LDFLAGS)

$(JNI_AUTO_HEADER): $(JAVA_CLASS).java Makefile $(OUT)
	javac $(JAVA_CLASS).java -d $(OUT) -h $(OUT)

.PHONY: clean
clean:
	rm bld/* -rf
	echo $(EXTRA_CFLAGS)
