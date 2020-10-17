cd ${0%/*} 2>/dev/null
echo $PWD/${0##*/}

rm .settings
rm -rf bin
mvn clean
mvn eclipse:clean

mv ~/h2/test.mv.db ~/Documents/study/database/JPA/jpa/h2
