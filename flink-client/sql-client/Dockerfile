FROM flink:1.13.2-scala_2.12-java11

RUN mkdir -p /opt/sql-client/lib
RUN mkdir -p /opt/sql-client/ddl

COPY bin/* /opt/sql-client/
COPY lib/* /opt/sql-client/lib/
COPY udf/* /opt/sql-client/lib/
COPY conf/* /opt/flink/conf/

WORKDIR /opt/sql-client
ENV SQL_CLIENT_HOME /opt/sql-client

COPY docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
