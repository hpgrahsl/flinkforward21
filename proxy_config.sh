#!/bin/sh

arg1=$1
arg2=$2

if [ "$#" -ne 2 ] || ([ $arg1 != "initial" ] && [ $arg1 != "read" ] && [ $arg1 != "read_write" ]) || ([ $arg2 != "kafka-dbz" ] && [ $arg2 != "flink-cdc" ]); then
    echo "usage: ./proxy_config.sh initial|read|read_write kafka-dbz|flink-cdc"
    exit 1
fi

NGINX_CONFIG_DIR=`pwd`/docker/.config/nginx

echo "using proxy configuration folder $NGINX_CONFIG_DIR"
echo "applying $arg1 config"

cp $NGINX_CONFIG_DIR/nginx_$arg1.conf $NGINX_CONFIG_DIR/nginx.conf

echo "restarting docker compose service nginx"

if [ $arg2 = 'kafka-dbz' ]; then
    docker-compose -f docker-compose-table-api-kafka-dbz.yaml restart nginx
fi

if [ $arg2 = 'flink-cdc' ]; then
    docker-compose -f docker-compose-sql-flink-cdc.yaml restart nginx
fi
