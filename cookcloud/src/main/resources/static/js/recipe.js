$(document).ready(function() {
	// recipeTypes를 서버에서 전달된 데이터로 초기화
	const recipeTypes = /*[[${recipeTypes}]]*/[];

	// 레시피 유형 select에 옵션 추가
	recipeTypes.forEach(function(entry) {
		const option = `<option value="${entry.key}">${entry.value.codeName}</option>`;
		$('#recipeType').append(option);
	});

	// 레시피 목록 (예시 데이터, 서버에서 받아온 실제 레시피로 교체해야 함)
	const recipes = /*[[${recipes}]]*/[];  // 서버에서 모든 레시피 목록을 전달받아야 함.

	// 레시피 유형 select 값 선택 시 동작
	$('#recipeTypeSelect').change(function() {
		const selectedRecipeType = $(this).val();  // 선택된 레시피 유형
		console.log("Selected Recipe Type:", selectedRecipeType);

		// 선택된 레시피 유형에 따라 레시피 목록을 필터링
		filterRecipesByType(selectedRecipeType);
	});

	// 레시피 필터링 함수
	function filterRecipesByType(recipeType) {
		let filteredRecipes = recipes;  // 기본값은 모든 레시피

		if (recipeType) {
			filteredRecipes = recipes.filter(function(recipe) {
				return recipe.typeCode === recipeType;  // recipe.typeCode는 실제 레시피 객체에서 유형을 나타내는 키
			});
		}

		displayRecipes(filteredRecipes);  // 필터링된 레시피를 화면에 표시하는 함수 호출
	}

	// 레시피 목록 화면에 표시하는 함수
	function displayRecipes(filteredRecipes) {
		const recipeList = $('#recipeList');
		recipeList.empty();  // 기존 레시피 목록을 비움

		filteredRecipes.forEach(function(recipe) {
			const recipeCard = `<div class="recipe-card">
                <h5>${recipe.title}</h5>
                <p>${recipe.description}</p>
                <span>${recipe.typeName}</span>  <!-- 레시피 유형 표시 -->
            </div>`;
			recipeList.append(recipeCard);  // 레시피 카드를 추가
		});
	}

	// 검색 버튼 클릭 시 해시태그 검색
	$('#hashtagSearch').click(function() {
		const hashtag = $('#hashtagSearch').val();
		console.log("Searching for Hashtag:", hashtag);
		// 해시태그에 맞는 레시피를 필터링하는 로직
		searchByHashtag(hashtag);
	});

	// 검색 입력 필드에 따른 레시피 검색 처리
	$('#searchInput').keyup(function() {
		const searchTerm = $(this).val();
		console.log("Searching for term:", searchTerm);
		// 검색어에 맞는 레시피를 필터링하는 로직
		searchRecipes(searchTerm);
	});

	// 해시태그 입력을 쉼표로 구분된 배열로 변환
	const hashtags = $("#hashtags").val();
	const hashtagArray = hashtags ? hashtags.split(',').map(item => item.trim()) : [];
	$("#hashtags").val(hashtagArray.join(", "));

	// 이미지 미리보기 기능
	$('#image').on('change', function(e) {
		var reader = new FileReader();
		reader.onload = function(e) {
			var image = new Image();
			image.src = e.target.result;
			// 여기에서 이미지 미리보기 설정을 추가할 수 있습니다.
		}
		reader.readAsDataURL(this.files[0]);
	});

	let offset = 0;
	const limit = 10;
	let loading = false;

	if (loading) return;
	loading = true;

	fetch(`/api/recipes?offset=${offset}&limit=${limit}`)
		.then(response => response.json())
		.then(data => {
			if (data.length > 0) {
				offset += limit;
			}
			loading = false;
		})
		.catch(error => {
			loading = false;
		});

});

// 무한 스크롤 이벤트
window.addEventListener("scroll", () => {
	if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
	}
});

