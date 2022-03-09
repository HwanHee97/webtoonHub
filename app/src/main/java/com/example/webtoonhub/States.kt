package com.example.webtoonhub

sealed class UiState{
    object Loading : UiState()
    object Empty : UiState()
    object Success: UiState()
    object Error: UiState()
}
//sealed  클래스는 Child 클래스의 종류를 제한하는 특성이 있어, 정의된 하위 클래스 외에 다른 하위 클래스는 존재하지 않는다는 것을 컴파일러에게 알려주는 것과 같은 효과를 낸다
//즉 when 문으로 모든 케이스에 대해서 처리가 되어야 하기 때문에 다른 클래스들은 else 구문이 들어가야 하지만,
// sealed class는 컴파일 시점에 하위 클래스들이 정해져 있기 때문에 else 없이도 클래스들을 관리할 수 있다.
