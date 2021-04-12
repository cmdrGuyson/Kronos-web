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
                    Add Student
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
                        <form method="POST" action="/add-student">
                            <div class="form-row">
                                <div class="col">
                                    <label for="inputUsername">Username</label>
                                    <input type="text" class="form-control select-filter" name="username" id="inputUsername" required>
                                </div>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputFirstName">First Name</label>
                                    <input type="text" class="form-control select-filter" name="firstName" id="inputFirstName" required>
                                </div>
                                <div class="col">
                                    <label for="inputLastName">Last Name</label>
                                    <input type="text" class="form-control select-filter" id="inputLastName" name="lastName" required>
                                </div>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputClass">Classes</label>
                                <select class="custom-select form-control select-filter" id="inputClass" name="classID" required>
                                    <c:forEach var="class" items="${classes}">
                                        <option value="${class.getClassID()}" >${class.getType()}-${class.getClassID()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <p class="model-info"><i>(Password of student will be the username in uppercase characters)</i></p>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Add Student
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>