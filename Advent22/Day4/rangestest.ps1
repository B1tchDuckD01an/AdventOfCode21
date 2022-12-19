$a = Get-Content .\input.txt
$score = 0

foreach($thing in $a)
{
Write-Host
Write-Host $thing
    $pairs = $thing.Split(",")
    foreach($pair in $pairs[0])
    {
        $numbers = $pair.Split("-")
        $left = ($numbers[0],$numbers[1])

    }
    foreach($pair in $pairs[1])
    {
        $numbers = $pair.Split("-")
        $right = ($numbers[0],$numbers[1])
    }
    Write-Host $left $right
    if(($left[0] -ge $right[0]) -and ($left[0] -le $right[1])){
            Write-Host range 2 in range 1
            $score ++
    }
    elseif(($right[0] -ge $left[0]) -and ($right[0] -le $left[1])){
            Write-Host range 1 in range 2
            $score++
    }


}
Write-Host $score

e

$data = Get-Content -Path .\input.txt
#$data = Get-Content -Path .\test.txt

$score = 0
$data | ForEach-Object {
    Write-Host "*********************"
    $left = $_.Split(',')[0]
    $leftStart = [int]$left.Split('-')[0]
    $leftEnd = [int]$left.Split('-')[1]
    Write-Host "Left " $left #"     " $leftStart $leftEnd

    $right = $_.Split(',')[1]
    $rightStart = [int]$right.Split('-')[0]
    $rightEnd = [int]$right.Split('-')[1]
    Write-Host "Right" $right #"     " $rightStart $rightEnd

    if (($leftStart -ge $rightStart) -and ($leftStart -le $rightEnd)) {
        $score++
        Write-Host "============ L in R $score"
    }
    elseif (($rightStart -ge $leftStart) -and ($rightStart -le $leftEnd)) {
        $score++
        Write-Host "============ R in L $score"
    }
}
Write-Host " There were $score matches."