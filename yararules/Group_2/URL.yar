rule dataUri : PB_Gen
{
    meta:
        score = 15
	description = "datauri"

    strings:
        $ = /(<object data="data:)/ nocase
	$ = /(<Anchor data="data:)/ nocase
	$ = /(<IFRAME data="data:)/ nocase
	$ = /(<Image data="data:)/ nocase
	$ = /(<EMBED SRC="data:)/ nocase

    condition:
        any of them
}

rule urlisip : PB_Gen
{
    meta:
        score = 17
	description = "urlisip"

    strings:
        $1 = /((https|http):\/\/(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))/ nocase

    condition:
        $1
}

rule urlhassexe : PB_Gen
{
    meta:
        score = 33
	description = "exe file in uri"

    strings:
        $1 = /(\.exe)/ nocase 
	$2 = /(\.bin)/ nocase

    condition:
        $1 or $2
}

rule shortURL : PB_Gen
{
    meta:
        score = 25
	description = "shortURL"

    strings:

	$ = /(bit\.ly\/)/ nocase
	$ = /(goo\.gl\/)/ nocase
	$ = /(tinyurl\.com\/)/ nocase
	$ = /(is\.gd\/)/ nocase
	$ = /(cli\.gs\/)/ nocase
	$ = /(pic\.gd\/)/ nocase
	$ = /(DwarfURL\.com\/)/ nocase
	$ = /(ow\.ly\/)/ nocase
	$ = /(yfrog\.com\/)/ nocase
	$ = /(migre\.me\/)/ nocase
	$ = /(ff\.im\/)/ nocase
	$ = /(tiny\.cc\/)/ nocase
	$ = /(url4\.eu\/)/ nocase
	$ = /(tr\.im\/)/ nocase
	$ = /(twit\.ac\/)/ nocase
	$ = /(su\.pr\/)/ nocase
	$ = /(twurl\.nl\/)/ nocase
	$ = /(snipurl\.com\/)/ nocase
	$ = /(BudURL\.com\/)/ nocase
	$ = /(short\.to\/)/ nocase
	$ = /(ping\.fm\/)/ nocase
	$ = /(Digg\.com\/)/ nocase
	$ = /(post\.ly\/)/ nocase
	$ = /(Just\.as\/)/ nocase
	$ = /(bkite\.com\/)/ nocase
	$ = /(snipr\.com\/)/ nocase
	$ = /(flic\.kr\/)/ nocase
	$ = /(loopt\.us\/)/ nocase
	$ = /(doiop\.com\/)/ nocase
	$ = /(twitthis\.com\/)/ nocase
	$ = /(htxt\.it\/)/ nocase
	$ = /(AltURL\.com\/)/ nocase
	$ = /(RedirX\.com\/)/ nocase
	$ = /(DigBig\.com\/)/ nocase
	$ = /(short\.ie\/)/ nocase
	$ = /(u\.ma\/)/ nocase
	$ = /(kl\.am\/)/ nocase
	$ = /(wp\.me\/)/ nocase
	$ = /(u\.nu\/)/ nocase
	$ = /(rubyurl\.com\/)/ nocase
	$ = /(om\.ly\/)/ nocase
	$ = /(linkbee\.com\/)/ nocase
	$ = /(Yep\.it\/)/ nocase
	$ = /(posted\.at\/)/ nocase
	$ = /(xrl\.us\/)/ nocase
	$ = /(metamark\.net\/)/ nocase
	$ = /(sn\.im\/)/ nocase
	$ = /(hurl\.ws\/)/ nocase
	$ = /(eepurl\.com\/)/ nocase
	$ = /(idek\.net\/)/ nocase
	$ = /(urlpire\.com\/)/ nocase
	$ = /(chilp\.it\/)/ nocase
	$ = /(moourl\.com\/)/ nocase
	$ = /(snurl\.com\/)/ nocase
	$ = /(xr\.com\/)/ nocase
	$ = /(lin\.cr\/)/ nocase
	$ = /(EasyURI\.com\/)/ nocase
	$ = /(zz\.gd\/)/ nocase
	$ = /(ur1\.ca\/)/ nocase
	$ = /(URL\.ie\/)/ nocase
	$ = /(adjix\.com\/)/ nocase
	$ = /(twurl\.cc\/)/ nocase
	$ = /(s7y\.us\/)/ nocase
	$ = /(EasyURL\.net\/)/ nocase
	$ = /(atu\.ca\/)/ nocase
	$ = /(sp2\.ro\/)/ nocase
	$ = /(Profile\.to\/)/ nocase
	$ = /(ub0\.cc\/)/ nocase
	$ = /(minurl\.fr\/)/ nocase
	$ = /(cort\.as\/)/ nocase
	$ = /(fire\.to\/)/ nocase
	$ = /(2tu\.us\/)/ nocase
	$ = /(twiturl\.de\/)/ nocase
	$ = /(to\.ly\/)/ nocase
	$ = /(BurnURL\.com\/)/ nocase
	$ = /(nn\.nf\/)/ nocase
	$ = /(clck\.ru\/)/ nocase
	$ = /(notlong\.com\/)/ nocase
	$ = /(thrdl\.es\/)/ nocase
	$ = /(spedr\.com\/)/ nocase
	$ = /(vl\.am\/)/ nocase
	$ = /(miniurl\.com\/)/ nocase
	$ = /(virl\.com\/)/ nocase
	$ = /(PiURL\.com\/)/ nocase
	$ = /(1url\.com\/)/ nocase
	$ = /(gri\.ms\/)/ nocase
	$ = /(tr\.my\/)/ nocase
	$ = /(Sharein\.com\/)/ nocase
	$ = /(urlzen\.com\/)/ nocase
	$ = /(fon\.gs\/)/ nocase
	$ = /(Shrinkify\.com\/)/ nocase
	$ = /(ri\.ms\/)/ nocase
	$ = /(b23\.ru\/)/ nocase
	$ = /(Fly2\.ws\/)/ nocase
	$ = /(xrl\.in\/)/ nocase
	$ = /(Fhurl\.com\/)/ nocase
	$ = /(wipi\.es\/)/ nocase
	$ = /(korta\.nu\/)/ nocase
	$ = /(shortna\.me\/)/ nocase
	$ = /(fa\.b\/)/ nocase
	$ = /(WapURL\.co\.uk\/)/ nocase
	$ = /(urlcut\.com\/)/ nocase
	$ = /(6url\.com\/)/ nocase
	$ = /(abbrr\.com\/)/ nocase
	$ = /(SimURL\.com\/)/ nocase
	$ = /(klck\.me\/)/ nocase
	$ = /(x\.se\/)/ nocase
	$ = /(2big\.at\/)/ nocase
	$ = /(url\.co\.uk\/)/ nocase
	$ = /(ewerl\.com\/)/ nocase
	$ = /(inreply\.to\/)/ nocase
	$ = /(TightURL\.com\/)/ nocase
	$ = /(a\.gg\/)/ nocase
	$ = /(tinytw\.it\/)/ nocase
	$ = /(zi\.pe\/)/ nocase
	$ = /(riz\.gd\/)/ nocase
	$ = /(hex\.io\/)/ nocase
	$ = /(fwd4\.me\/)/ nocase
	$ = /(bacn\.me\/)/ nocase
	$ = /(shrt\.st\/)/ nocase
	$ = /(ln-s\.ru\/)/ nocase
	$ = /(tiny\.pl\/)/ nocase
	$ = /(o-x\.fr\/)/ nocase
	$ = /(StartURL\.com\/)/ nocase
	$ = /(jijr\.com\/)/ nocase
	$ = /(shorl\.com\/)/ nocase
	$ = /(icanhaz\.com\/)/ nocase
	$ = /(updating\.me\/)/ nocase
	$ = /(kissa\.be\/)/ nocase
	$ = /(hellotxt\.com\/)/ nocase
	$ = /(pnt\.me\/)/ nocase
	$ = /(nsfw\.in\/)/ nocase
	$ = /(xurl\.jp\/)/ nocase
	$ = /(yweb\.com\/)/ nocase
	$ = /(urlkiss\.com\/)/ nocase
	$ = /(QLNK\.net\/)/ nocase
	$ = /(w3t\.org\/)/ nocase
	$ = /(lt\.tl\/)/ nocase
	$ = /(twirl\.at\/)/ nocase
	$ = /(zipmyurl\.com\/)/ nocase
	$ = /(urlot\.com\/)/ nocase
	$ = /(a\.nf\/)/ nocase
	$ = /(hurl\.me\/)/ nocase
	$ = /(URLHawk\.com\/)/ nocase
	$ = /(Tnij\.org\/)/ nocase
	$ = /(4url\.cc\/)/ nocase
	$ = /(firsturl\.de\/)/ nocase
	$ = /(Hurl\.it\/)/ nocase
	$ = /(sturly\.com\/)/ nocase
	$ = /(shrinkster\.com\/)/ nocase
	$ = /(ln-s\.net\/)/ nocase
	$ = /(go2cut\.com\/)/ nocase
	$ = /(liip\.to\/)/ nocase
	$ = /(shw\.me\/)/ nocase
	$ = /(XeeURL\.com\/)/ nocase
	$ = /(liltext\.com\/)/ nocase
	$ = /(lnk\.gd\/)/ nocase
	$ = /(xzb\.cc\/)/ nocase
	$ = /(linkbun\.ch\/)/ nocase
	$ = /(href\.in\/)/ nocase
	$ = /(urlbrief\.com\/)/ nocase
	$ = /(2ya\.com\/)/ nocase
	$ = /(safe\.mn\/)/ nocase
	$ = /(shrunkin\.com\/)/ nocase
	$ = /(bloat\.me\/)/ nocase
	$ = /(krunchd\.com\/)/ nocase
	$ = /(minilien\.com\/)/ nocase
	$ = /(ShortLinks\.co\.uk\/)/ nocase
	$ = /(qicute\.com\/)/ nocase
	$ = /(rb6\.me\/)/ nocase
	$ = /(urlx\.ie\/)/ nocase
	$ = /(pd\.am\/)/ nocase
	$ = /(go2\.me\/)/ nocase
	$ = /(tinyarro\.ws\/)/ nocase
	$ = /(tinyvid\.io\/)/ nocase
	$ = /(lurl\.no\/)/ nocase
	$ = /(ru\.ly\/)/ nocase
	$ = /(lru\.jp\/)/ nocase
	$ = /(rickroll\.it\/)/ nocase
	$ = /(togoto\.us\/)/ nocase
	$ = /(ClickMeter\.com\/)/ nocase
	$ = /(hugeurl\.com\/)/ nocase
	$ = /(tinyuri\.ca\/)/ nocase
	$ = /(shrten\.com\/)/ nocase
	$ = /(shorturl\.com\/)/ nocase
	$ = /(Quip-Art\.com\/)/ nocase
	$ = /(urlao\.com\/)/ nocase
	$ = /(a2a\.me\/)/ nocase
	$ = /(tcrn\.ch\/)/ nocase
	$ = /(goshrink\.com\/)/ nocase
	$ = /(DecentURL\.com\/)/ nocase
	$ = /(decenturl\.com\/)/ nocase
	$ = /(zi\.ma\/)/ nocase
	$ = /(1link\.in\/)/ nocase
	$ = /(sharetabs\.com\/)/ nocase
	$ = /(shoturl\.us\/)/ nocase
	$ = /(fff\.to\/)/ nocase
	$ = /(hover\.com\/)/ nocase
	$ = /(lnk\.in\/)/ nocase
	$ = /(jmp2\.net\/)/ nocase
	$ = /(dy\.fi\/)/ nocase
	$ = /(urlcover\.com\/)/ nocase
	$ = /(2pl\.us\/)/ nocase
	$ = /(tweetburner\.com\/)/ nocase
	$ = /(u6e\.de\/)/ nocase
	$ = /(xaddr\.com\/)/ nocase
	$ = /(gl\.am\/)/ nocase
	$ = /(dfl8\.me\/)/ nocase
	$ = /(go\.9nl\.com\/)/ nocase
	$ = /(gurl\.es\/)/ nocase
	$ = /(C-O\.in\/)/ nocase
	$ = /(TraceURL\.com\/)/ nocase
	$ = /(liurl\.cn\/)/ nocase
	$ = /(MyURL\.in\/)/ nocase
	$ = /(urlenco\.de\/)/ nocase
	$ = /(ne1\.net\/)/ nocase
	$ = /(buk\.me\/)/ nocase
	$ = /(rsmonkey\.com\/)/ nocase
	$ = /(cuturl\.com\/)/ nocase
	$ = /(turo\.us\/)/ nocase
	$ = /(sqrl\.it\/)/ nocase
	$ = /(iterasi\.net\/)/ nocase
	$ = /(tiny123\.com\/)/ nocase
	$ = /(EsyURL\.com\/)/ nocase
	$ = /(urlx\.org\/)/ nocase
	$ = /(IsCool\.net\/)/ nocase
	$ = /(twitterpan\.com\/)/ nocase
	$ = /(GoWat\.ch\/)/ nocase
	$ = /(poprl\.com\/)/ nocase
	$ = /(njx\.me\/)/ nocase
	$ = /(pastebin)/ nocase

    condition:
        any of them
}

rule bad_tld : PB_Gen
{
    meta:
        score = 15
	description = "known bad TLD"

    strings:
        $1 = /(\.su\/|\.top\/|\.link\/|\.pw\/|\.xyz\/|\.ga\/|\.gq\/|\.tk\/|\.ru\/|\.bid\/|\.win\/|\.club\/|\.trade\/|\.info\/|\.biz\/|\.cc\/|\.qa\/|\.ws\/)/ nocase

    condition:
        $1
}
