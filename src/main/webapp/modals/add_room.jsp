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
                    Add Room
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
                        <form method="POST" action="/add-room">
                            <div class="form-row" style="padding: 5px;">
                                <label for="inputType">Type</label>
                                <select class="custom-select form-control select-filter" id="inputType" name="type" required>
                                    <c:forEach var="type" items="${types}">
                                        <option value="${type}">${type}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-row form-row-model-text">
                                <div class="col">
                                    <label for="inputDescription">Description</label>
                                    <textarea class="form-control text-area-round" id="inputDescription" name="description" maxlength="255" rows="4" required></textarea>
                                </div>
                            </div>
                            <hr />
                            <button class="btn btn-primary" type="submit" value="submit">
                                Add Room
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>