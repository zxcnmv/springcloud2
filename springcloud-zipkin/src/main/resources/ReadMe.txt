运行如下方式：
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
or
docker run -d -p 9411:9411 openzipkin/zipkin