1. Setup Maven

http://maven.apache.org/install.html

2. Checkout and build

cd ${YOUR_WORK_DIR}

git clone git@github.com:mto/sale-analytics.git

cd sale-analytics

mvn clean install

3. Open project on Eclipse

cd sale-analytics

and run following command to generate .project and .classpath files

mvn eclipse:eclipse 

Open project from Eclipse