
const noteList = document.getElementById("noteList");
const noteListUl = document.getElementById("noteListUl");
getAllNotes();

function addNoteElement(id, title, content) {

    let html = `<li>
                    <div class="note-element">
                        <a href="/edit/${id}">
                            <p>${title}</p>
                            <p>${content}</p>
                        </a>
                        <button onclick="deleteNote(${id})">Delete Note</button>
                    </div>
                </li>`;

    noteListUl.innerHTML = html;
}

async function getAllNotes() {
    try {
        const res = await fetch("http://localhost:8080/api/notes/all");

        const notes = await res.json();

        for (note of notes) {
            addNoteElement(note.id, note.title, note.content);
        }

    } catch(error) {
        alert(error);
    }
}

async function deleteNote(id) {
    try {
        const res = await fetch(`http://localhost:8080/api/notes/${id}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (res.ok) {
            console.log(res);
        }
        else {
            alert(res);
        }
    } catch (error) {
        alert(error);
    }
}