#!/bin/bash
source /etc/profile

cd $(dirname $0)

exec java -Xmx1024M -Xms512M -cp conf:lib/* org.example.MavenpApp
