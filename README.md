# morechat

## Requirements

* JDK 8
* VoiceText API Key
 * https://cloud.voicetext.jp/webapi

## Usage

### Build

``` sh
git clone https://github.com/emag/morechat.git
cd morechat
./gradlew distZip
```

You can find the client/server binaries in the following directories.

* `morechat-server/build/distributions/morechat-server-x.y.z.zip`
* `morechat-client/build/distributions/morechat-client-x.y.z.zip`

### Run

#### Server

``` sh
unzip morechat-server-x.y.z.zip
cd morechat-server-x.y.z/bin
./morechat-server
```

#### Client

``` sh
unzip morechat-client-x.y.z.zip
cd morechat-client-x.y.z/bin
MORECHAT_CLIENT_OPTS="-Dvoicetext.apikey=your_voicetext_api_key" ./morechat-client
```
