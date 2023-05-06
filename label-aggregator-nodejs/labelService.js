const axios = require("axios");

exports.getLabels = async (key, nb) => {
    let range = [...Array(nb).keys()]
    let responses = await Promise.all(range.map(i => axios.get(`http://localhost:8090/api/${key}-${i}`)))
    let labels = responses.map(response => response.data).map(data => data.label)
    return { key: key, labels: labels }
}