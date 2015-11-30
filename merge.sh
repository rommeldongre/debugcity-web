cd ~/dev/workspace/dbctweb
git checkout -- build/*
git pull
ant
./deploy-local.sh 
scp -i ~/dev/rom-test.pem build/dbctv1.war ubuntu@52.74.250.44:/tmp

