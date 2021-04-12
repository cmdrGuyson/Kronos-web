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
                    Add Lecturer
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
                        <form method="POST" action="/add-lecturer">
                            <div class="form-row">
                                <div class="col">
                                    <label for="inputFirstName">First Name</label>
                                    <input type="text" class="form-control select-filter" name="firstName" id="inputFirstName" required>
                                </div>
                                <div class="col">
                                    <label for="inputLastName">Last Name</label>
                                    <input type="text" class="form-control select-filter" id="inputLastName" name="lastName" required>
                                </div>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputEmail">Email</label>
                                    <input type="email" class="form-control select-filter" name="email" id="inputEmail" required>
                                </div>
                            </div>
                            <div class="form-row form-row-modal">
                                <label for="inputType">Type</label>
                                <select class="custom-select form-control select-filter" id="inputType" name="type" required>
                                    <c:forEach var="type" items="${types}">
                                        <option value="${type}" >${type}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Add Lecturer
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>