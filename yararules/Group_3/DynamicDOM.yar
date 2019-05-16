rule abusedfunctions : PB_Gen
{
    meta:
        score = 10
	description = "commonly abused js functions"

    strings:
        $1 = /(unescape\()/ nocase
	$2 = /(decompress\()/ nocase
	$3 = /(Uint8Array\()/ nocase
	$4 = /(inflate\()/ nocase
	$5 = /(setTimeout\()/ nocase
	$6 = /(Math\.pow)/ nocase
	$7 = /(Math\.round)/ nocase
	$8 = /(deflate\()/ nocase
	
    condition:
        #1 > 30 or #2 > 2 or #3 > 2 or #4 > 1 or #5 > 15 or #6 > 30 or #7 > 30 or #8 > 1
}

rule createhtmltags : PB_Gen
{
    meta:
        score = 3
	description = "create html tags"

    strings:
        $1 = /(createElement\('script'\))/ nocase
	$2 = /(createElement\('iframe'\))/ nocase

    condition:
        $1 or $2
}

rule createhtmltags_many : PB_Gen
{
    meta:
        score = 12
	description = "create html tags in huge amounts"

    strings:
        $1 = /(createElement\('script'\))/ nocase
	$2 = /(createElement\('iframe'\))/ nocase

    condition:
        #1 > 30 or #2 > 10
}

rule datewithframe : PB_Gen
{
    meta:
        score = 3
	description = "datewithframe"

    strings:
        $ = /(iframe)/ nocase
	$ = /(date\()/ nocase

    condition:
        2 of them
}

rule scripttag : PB_Gen
{
    meta:
        score = 1
	description = "scriptTag"

    strings:
        $ = /(<\/script>)/ nocase
	$ = /(<script)/ nocase
	$ = /(script)/ nocase

    condition:
        1 of them
}

rule navigation : PB_Gen
{
    meta:
        score = 10
	description = "location and navigation"

    strings:
	$1 = /(window\.location)/ nocase
	$2 = /(window\.navigate)/ nocase

    condition:
        #1 > 2 or #2 > 2
}

rule InnerHTML : PB_Gen
{
    meta:
        score = 15
	description = "10xInnerHTML"

    strings:
        $inner = /(innerhtml)/ nocase

    condition:
        #inner > 150
}

rule documentWrite : PB_Gen
{
    meta:
        score = 15
	description = "documentWrite"

    strings:
        $1 = /(document[\d].write)/ nocase
	$2 = /(document[\d\d].write)/ nocase

    condition:
        #1 > 15 or #2 > 30
}

