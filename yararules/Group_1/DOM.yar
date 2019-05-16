rule evalfun : PB_Gen
{
    meta:
        score = 12
	description = "PureEvalFun"

    strings:
	$ = /(eval\(function)/ nocase 
	$ = /(eval \(function)/ nocase

    condition:
        any of them
}

rule eval : PB_Gen
{
    meta:
        score = 8
	description = "PureEval"

    strings:
	$1 = /(eval\()/ nocase

    condition:
        #1 > 10
}

rule evalflood : PB_Gen
{
    meta:
        score = 16
	description = "PureEval"

    strings:
	$1 = /(eval\()/ nocase

    condition:
        #1 > 30
}

rule ccOninMozilla : PB_Gen
{
    meta:
        score = 7
	description = "Hidecode in comments mozilla"

    strings:
	$ = /(@cc_on)/ nocase

    condition:
        any of them
}

rule iframetag : PB_Gen
{
    meta:
        score = 5
	description = "iframetag"

    strings:
	$1 = /(iframe)/ nocase

    condition:
        #1 > 6
}


rule hiddeniframetag : PB_Gen
{
    meta:
        score = 15
	description = "Hidden iframetag"

    strings:
        $1 = /(width:0|height:0|border:0)/ nocase
	$2 = /(border:none|border: none)/ nocase
	$3 = /(iframe)/ nocase
	$4 = /(position:absolute|position: absolute)/ nocase

    condition:
        $1 and $2 and $3 and $4
}


rule abusedtags : PB_Gen
{
    meta:
        score = 10
	description = "commonly abused tags"

    strings:
        $ = /(<svg onload=)/ nocase
	$ = /(<object onload=)/ nocase
	$ = /(<script onload=)/ nocase

    condition:
        any of them
}


