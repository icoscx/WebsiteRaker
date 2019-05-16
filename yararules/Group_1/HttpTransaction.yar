rule metarefresh : PB_Gen
{
    meta:
        score = 5
	description = "metarefresh"

    strings:
        $ = /(http-equiv='refresh')/ nocase
	$ = /(http-equiv="refresh")/ nocase

    condition:
        any of them
}

rule httpredirection : PB_Gen
{
    meta:
        score = 3
	description = "httpredirection"

    strings:
	$ = /(302 Found)/ nocase
	$ = /(304 Not Modified)/ nocase
	$ = /(307 Temporary Redirect)/ nocase
	$ = /(308 Permanent Redirect)/ nocase
	$ = /(301 Moved Permanently)/ nocase

    condition:
        any of them
}

rule httpredirection_moved : PB_Gen
{
    meta:
        score = 15
	description = "httpredirection_moved"

    strings:
        $1 = /(301 Moved Permanently)/ nocase

    condition:
        #1 > 3
}

rule mimeIsExe : PB_Gen
{
    meta:
        score = 50
	description = "server says mimetype is exe"

    strings:
        $ = /(application\/x-msdownload)/ nocase
	$ = /(application\/x-msdownload)/ nocase
	$ = /(application\/octet-stream)/ nocase

    condition:
        any of them
}

rule servertalksbase64 : PB_Gen
{
    meta:
        score = 5
	description = "server responds with base64 or binary"

    strings:
        $1 = /(Content-Transfer-Encoding: base64|Content-Transfer-Encoding:base64)/ nocase
	$2 = /(Content-Transfer-Encoding: BINARY|Content-Transfer-Encoding:BINARY)/ nocase

    condition:
        $1 or $2
}


