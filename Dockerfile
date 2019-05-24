FROM clojure
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN lein deps
CMD ["lein", "run"]
