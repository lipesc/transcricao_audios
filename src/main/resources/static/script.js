function showErrorPopup(message) {
    document.getElementById('errorMessage').innerText = message;
    document.getElementById('errorPopup').style.display = 'block';
}

function closePopup() {
    document.getElementById('errorPopup').style.display = 'none';
}



document.getElementById('audioFile').addEventListener('change', function() {
    const file = this.files[0];

    if (file) {
        const formData = new FormData();
        formData.append('file', file);

        const loadingElement = document.getElementById('loading');
        loadingElement.classList.remove('hidden');


        fetch('/audio/upload', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById('summary').textContent = data.summary || "No summary available.";
                document.getElementById('chapters').textContent = data.chapters
                    .map(chapter => `Start: ${chapter.start}, End: ${chapter.end}, Headline: ${chapter.headline}`)
                    .join('\n') || "No chapters available.";
                document.getElementById('language').textContent = data.language || "Language not detected.";
                document.getElementById('transcription').textContent = data.transcription || "No transcription available.";
            })

            .catch(error => showErrorPopup('Error: PAYLOAD_TOO_LARGE ' + error))
            .finally(() => {
                loadingElement.classList.add('hidden');
            });
    }
});
