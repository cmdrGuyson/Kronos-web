<div
        class="modal fade"
        id="addModal"
        tabindex="-1"
        role="dialog"
        aria-labelledby="manageGroupModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    Add Module
                </h5>
                <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>



            <div class="modal-body">
                <div class="card-body">
                    <div class="form-body">
                        <form method="POST" action="/add-module">
                            <div class="form-row">
                                <div class="col">
                                    <label for="inputModuleName">Module Name</label>
                                    <input type="text" class="form-control select-filter" name="name" id="inputModuleName" required>
                                </div>
                                <div class="col">
                                    <label for="inputCredits">Credits</label>
                                    <input type="number" class="form-control select-filter" id="inputCredits" name="credits" required>
                                </div>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputLecturer">Lecturer</label>
                                <select class="custom-select form-control select-filter" id="inputLecturer" name="lecturerID" required>
                                    <c:forEach var="lecturer" items="${lecturers}">
                                        <option value="${lecturer.getLecturerID()}" >${lecturer.getFirstName()} ${lecturer.getLastName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputDescription">Description</label>
                                    <textarea class="form-control" id="inputDescription" name="description" maxlength="255" required></textarea>
                                </div>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Add Module
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>