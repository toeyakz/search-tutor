package com.example.searchtutor.data.response


data class CategoryNameResponse(
    val isSuccessful: Boolean,
    val data: List<CategoryName>?
) {
    data class CategoryName(
        val g_id: Int?,
        val ca_name: String?
    )
}

data class CategoryResponse(
    val isSuccessful: Boolean,
    val data: List<Category>?
) {
    data class Category(
        val ca_id: Int?,
        val ca_name: String?
    )
}

data class AddGroupCategoryResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class DeleteCourseResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class UpdateCourseResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class AddCourseResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class UpdateGroupCategoryResponse(
    val isSuccessful: Boolean,
    val message: String?
)

data class GroupCourseResponse(
    val isSuccessful: Boolean,
    val data: List<GroupCourse>?
) {
    data class GroupCourse(
        val gc_id: Int?,
        val g_id: Int?,
        val t_id: Int?,
        val gc_name: String?,
        val gc_detail: String?,
        val gc_price: Int?
    )
}



