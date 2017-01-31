

function sendRequest() {
   var httpRequest = new XMLHttpRequest();

    var url = "https://utas.external.s2.fut.ea.com/ut/game/fifa17/tradepile";
    httpRequest.open ("POST", url, true);    // async
    httpRequest.withCredentials = true;
   httpRequest.setRequestHeader ("X-HTTP-Method-Override", "GET");
   httpRequest.setRequestHeader ("X-Requested-With", "ShockwaveFlash/24.0.0.194");
   httpRequest.setRequestHeader ("X-UT-Embed-Error", "true");
   httpRequest.setRequestHeader ("X-UT-PHISHING-TOKEN", "3722162985982886031");
   httpRequest.setRequestHeader ("X-UT-SID", "1ce37602-2c2e-4a41-856c-8135069eda05");
    httpRequest.setRequestHeader ("Accept", "application/json");
   // httpRequest.setRequestHeader ("Accept-Encoding", "gzip, deflate, lzma, br");
    httpRequest.setRequestHeader ("Accept-Language", "en-US,en;q=0.8,uk;q=0.6");
    httpRequest.setRequestHeader ("Cache-Control", "no-cache");
   // httpRequest.setRequestHeader ("Connection", "keep-alive");
    httpRequest.setRequestHeader ("Content-Type", "application/json");
    //httpRequest.setRequestHeader ("Host", "utas.external.s2.fut.ea.com");
   // httpRequest.setRequestHeader ("Origin", "https://www.easports.com");
    httpRequest.setRequestHeader ("Pragma", "no-cache");
    //httpRequest.setRequestHeader ("Referer", "https://www.easports.com/iframe/fut17/bundles/futweb/web/flash/FifaUltimateTeam.swf?cl=164808");

    httpRequest.onreadystatechange = OnStateChange;
    httpRequest.send (null);
}

 function OnStateChange () {
        debugger;
    if (httpRequest.readyState == 0 || httpRequest.readyState == 4) {
        if (IsRequestSuccessful (httpRequest)) {    // defined in ajax.js
            alert ("Request complete.");
        }
        else {
            alert ("Operation failed.");
        }
    }
}

sendRequest()