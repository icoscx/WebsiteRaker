# WebsiteRaker

Raker Rework

VM: -Xms512m -Xmx2048m

Ubuntu 16.04; 18.04 supported with OpenJDK11

Required Libs:
./WebsiteRaker/lib/

Required Tools:
./WebsiteRaker/malware-jail/  and  YARA 3.9.0+

Setupconfig: ./rakerConfig.json


java -classpath
./out/production/WebsiteRaker
./lib/htmlunit-driver-2.35.0-jar-with-dependencies.jar
./lib/json-20180813.jar
com.pure.Main


## License ##

MIT License

Copyright (c) 2018 Ivo Pure

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
