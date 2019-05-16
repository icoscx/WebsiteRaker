rule charcode : PB_Gen
{
    meta:
        score = 20
	description = "charcode"

    strings:
        $1 = /(fromCharCode)/ nocase
	$2 = /(unescape\()/ nocase
	$3 = /(charCodeAt\()/ nocase

    condition:
        #1 > 30 or #2 > 30 or #3 > 45
}

rule manydocumentWrite : PB_Gen
{
    meta:
        score = 20
	description = "flood of document write"

    strings:
        $1 = /(document.write)/ nocase

    condition:
        #1 > 150 
}

rule hex : PB_Gen
{
    meta:
	score = 10
        description = "This rule finds hex strings"
    strings:
        $a = /(0[xX][0-9a-fA-F]{2})/ nocase
	$b = /([xX][0-9a-fA-F]{2})/ nocase
    condition:
        #a > 50 or #b > 50
}

rule base64FunCalls : PB_Gen  
{ 
	meta: 
		score = 10 
		description = "base64calls" 
	strings: 
		$f = /(atob|btoa|;base64|base64,|encode64)/ nocase
	condition: 
		$f
}

rule genericJsObfuscation : PB_Gen  
{
	meta:
		score = 10
		description = "Generic JS obf"
	strings:
		$string0 = /eval\(([\s]+)?(unescape|atob)\(/ nocase
		$string1 = /var([\s]+)?([a-zA-Z_$])+([a-zA-Z0-9_$]+)?([\s]+)?=([\s]+)?\[([\s]+)?\"\\x[0-9a-fA-F]+/ nocase
		$string2 = /var([\s]+)?([a-zA-Z_$])+([a-zA-Z0-9_$]+)?([\s]+)?=([\s]+)?eval;/
	condition:
		any of them
}
