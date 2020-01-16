#!/usr/bin/env sh

# 当发生错误时中止脚本
set -e

#推送到原仓库
git push origin master
#推送到码云
git push gitee master

cd -