#!/bin/sh

arg=$1

if [ "$#" -ne 1 ] || ([ $arg != "initial" ] && [ $arg != "read" ] && [ $arg != "read_write" ]); then
    echo "usage: ./proxy_config.sh initial|read|read_write"
    exit 1
fi

NGINX_CONFIG_DIR=`pwd`/docker/.config/nginx

# echo "using proxy configuration folder $NGINX_CONFIG_DIR"
echo "applying $arg config"

cp $NGINX_CONFIG_DIR/nginx_$arg.conf $NGINX_CONFIG_DIR/nginx.conf

echo "restarting docker compose service nginx"

docker-compose restart nginx
