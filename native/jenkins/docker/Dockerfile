# build slave
#
# VERSION               0.0.1

FROM     centos:centos6
MAINTAINER Rahul Pandey "rahul@kosei.me"

RUN rpm -Uvh http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm
# make sure the package repository is up to date
RUN yum upgrade -y

RUN yum install -y which emacs-nox wget openssh-server git gcc gcc-c++ cmake openssl-devel fontconfig-devel libpng-devel tar bzip2 ghostscript ghostscript-devel libXext-devel lcms lcms-devel bzip2-devel libtiff-devel
RUN wget -nv http://public-repo-1.hortonworks.com/HDP/centos6/2.x/updates/2.0.12.0/hdp.repo -O /etc/yum.repos.d/hdp.repo
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y hadoop-source apache-maven
RUN cd /usr/src/hadoop/hadoop-tools/hadoop-pipes && mvn compile -Pnative
RUN cd /root && wget http://www.libarchive.org/downloads/libarchive-3.1.2.tar.gz && tar -xvzf libarchive-3.1.2.tar.gz
RUN cd /root/libarchive-3.1.2 && ./configure --without-xml2 --without-lzo2 --without-lzma --without-lzmadec --without-iconv --without-libiconv-prefix --without-nettle --without-openssl --without-expat && make && make install
RUN cd /root && wget https://protobuf.googlecode.com/files/protobuf-2.5.0.tar.bz2 && tar -xvjf protobuf-2.5.0.tar.bz2
RUN cd /root/protobuf-2.5.0 && ./configure && make && make check && make install
RUN cd /root && wget http://www.imagemagick.org/download/ImageMagick-6.8.9-7.tar.gz && tar -xvzf ImageMagick-6.8.9-7.tar.gz
RUN cd /root/ImageMagick-*  && ./configure && make && make check && make install
RUN mkdir /var/run/sshd
RUN echo 'root:Abc123' |chpasswd
RUN service sshd start
RUN service sshd stop

EXPOSE 22
CMD    ["/usr/sbin/sshd", "-D"]
