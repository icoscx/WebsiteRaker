rule wscript : PB_Dyn
{ 
    meta:
        score = 40
	description = "wscript elements"

    strings:
        $1 = /(ExpandEnvironmentStrings)/ nocase
	$2 = /(WScript\.Shell)/ nocase
	$3 = /(TEMP)/ nocase
	$4 = /(MSXML2\.XMLHTTP)/ nocase
	$5 = /(MSXML2.XMLHTTP.[\d].\.open)/ nocase
	$6 = /(MSXML2.XMLHTTP.[\d\d].\.open)/ nocase
	$7 = /(responsebody\.get\()/ nocase
	$8 = /(ADODB\.Stream)/ nocase
	$9 = /(HTTP\/404)/ nocase
	$10 = /(LoadFromFile\()/ nocase
	$11 = /(MathRandom\()/ nocase

    condition:
        5 of them
}

rule wscriptYear : PB_Dyn
{
    meta:
        score = 5
	description = "wscript yearcheck"

    strings:
        $1 = /(getyear)/ nocase
	$2 = /(geatyear\()/ nocase
	$3 = /(date\()/ nocase

    condition:
        2 of them
}

rule wscriptMore : PB_Dyn
{
    meta:
        score = 40
	description = "wscript pack2"

    strings:
	$1 = /(Write\()/ nocase
	$2 = /(WriteAll\()/ nocase
	$3 = /(run\()/ nocase
	$4 = /(\.SaveToFile\()/ nocase
	$5 = /(GetSecialFolder\([\d]\))/ nocase
	$6 = /(open\()/ nocase
	$7 = /(\.exe)/ nocase

    condition:
        3 of them
}

rule wscriptEvenMore : PB_Dyn
{
    meta:
        score = 40
	description = "wscript pack3"

    strings:
	$1 = /(WScript\.Sleep\()/ nocase
	$2 = /(FileExists\()/ nocase
	$3 = /(send\()/ nocase
	$4 = /(WScript\.Shell.Environment\()/ nocase
	$5 = /(\.dll|\.exe)/ nocase

    condition:
        2 of them
}

rule wscriptMoreThree : PB_Dyn
{
    meta:
        score = 50
	description = "wscript shell creation cmd"

    strings:
        $1 = /(WScript\.CreateObject\()/ nocase
	$2 = /(WScript\.Shell)/ nocase
	$3 = /(cmd\.exe)/ nocase

    condition:
        $1 and $2 and $3
}

rule hideCodeParseComment : PB_Dyn
{
    meta:
        score = 10
	description = "Hidecode hideCodeParseComment"

    strings:
	$ = /(@cc_on)/ nocase

    condition:
        any of them
}
