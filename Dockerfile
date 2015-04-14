#
# Install CloudOpting ToscaUI Manager
#
# https://github.com/CloudOpting/ToscaUI
#

# Pull base image.
FROM dockerfile/ubuntu

MAINTAINER Xavier Cases Camats version: 0.1

########################
#
# JAVA
#

# Install Oracle Java 7 JDK
RUN \
  echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java7-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk7-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-7-oracle

########################
#
# TOMCAT 8
#

# Define CATALINA_HOME variables.
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

# Create tomcat directory.
RUN mkdir -p "$CATALINA_HOME"
WORKDIR $CATALINA_HOME

# See https://www.apache.org/dist/tomcat/tomcat-8/KEYS
RUN gpg --keyserver pool.sks-keyservers.net --recv-keys \
	05AB33110949707C93A279E3D3EFE6B686867BA6 \
	07E48665A34DCAFAE522E5E6266191C37C037D42 \
	47309207D818FFD8DCD3F83F1931D684307A10A5 \
	541FBE7D8F78B25E055DDEE13C370389288584E7 \
	61B832AC2F1C5A90F0F9B00A1C506407564C17A3 \
	79F7026C690BAA50B92CD8B66A3AD3F4F22C4FED \
	9BA44C2621385CB966EBA586F72C284D731FABEE \
	A27677289986DB50844682F8ACB77FC2E86E29AC \
	A9C5DF4D22E99998D9875A5110C01C5A2F6059E7 \
	DCFD35E0BF8CA7344752DE8B6FB21E8933C60243 \
	F3A04C595DB5B6A5F1ECA43E3B7BBB100D811BBE \
	F7DA48BB64BCB84ECBA7EE6935CD23C10D498E23

# Define TOMCAT variables
ENV TOMCAT_MAJOR 8
ENV TOMCAT_VERSION 8.0.21
ENV TOMCAT_TGZ_URL https://www.apache.org/dist/tomcat/tomcat-$TOMCAT_MAJOR/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz

# Install Tomcat 8
RUN curl -SL "$TOMCAT_TGZ_URL" -o tomcat.tar.gz \
	&& curl -SL "$TOMCAT_TGZ_URL.asc" -o tomcat.tar.gz.asc \
	&& gpg --verify tomcat.tar.gz.asc \
	&& tar -xvf tomcat.tar.gz --strip-components=1 \
	&& rm bin/*.bat \
	&& rm tomcat.tar.gz*

########################    
# 
# MAVEN
#

# Define MAVEN_VERSION variable
ENV MAVEN_VERSION 3.3.1

# Install Maven 3
RUN curl -sSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Define MAVEN_HOME variable
ENV MAVEN_HOME /usr/share/maven

########################
# 
# GIT
# 
# Install Git
RUN apt-get update && \
  apt-get install -y git
   
######################## 
#
# ToscaUI Manager
#

# Install ToscaUI
RUN \
  cd  && \
  git clone https://github.com/CloudOpting/ToscaUI.git && \
  cd ToscaUI && \
  mvn clean install -DskipTests && \
  cp ./target/ToscaUI*.war $CATALINA_HOME/webapps/ToscaUI.war && \
  cd

# Expose Tomcat default port
EXPOSE 8080

#Execute Tomcat with the ToscaUI Manager
CMD ["catalina.sh", "run"]