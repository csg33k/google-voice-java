WTF.. seriously? We forked off a JSON lib to change the behavior? 

this is an old copy of:  


        <dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20140107</version>
        </dependency>
        
The version might not be right, but essentially gvjava was prepended
to all the package names.  everything in gvjava needs to be removed and 
whatever 'special' behavior was added needs to be addressed.


        
