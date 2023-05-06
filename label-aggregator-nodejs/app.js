const http = require("http");
const url = require("url");
const labelService = require("./labelService");

const server = http.createServer(async (req, res) => {
  const parsedUrl = url.parse(req.url, true);

  const pattern = /^\/api\/([^/]+)\/async$/;
  const match = req.url.split("?")[0].match(pattern);

  if (!match) {
    res.statusCode = 404;
    res.end("Page not found");
    return;
  }

  try {
    var nb = parsedUrl.query.nb ? parseInt(parsedUrl.query.nb) : 3
    var results = await labelService.getLabels(match[1], nb);

    res.statusCode = 200;
    res.setHeader("Content-Type", "application/json");
    res.end(JSON.stringify(results));
  } catch (err) {
    console.error(err)
    res.statusCode = 500;
    res.end("Internal Server Error");
  }
});

const hostname = "127.0.0.1";
const port = 8080;
server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});